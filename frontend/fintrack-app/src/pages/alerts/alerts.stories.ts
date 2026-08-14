import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsPage } from './alerts.component';

const meta: Meta<AlertsPage> = {
  title: 'Components/AlertsPage',
  component: AlertsPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsPage>;

export const Primary: Story = {
  args: {},
};
