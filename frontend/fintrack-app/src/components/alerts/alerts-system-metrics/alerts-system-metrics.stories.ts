import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsSystemMetrics } from './alerts-system-metrics.component';

const meta: Meta<AlertsSystemMetrics> = {
  title: 'Components/AlertsSystemMetrics',
  component: AlertsSystemMetrics,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsSystemMetrics>;

export const Primary: Story = {
  args: {},
};
