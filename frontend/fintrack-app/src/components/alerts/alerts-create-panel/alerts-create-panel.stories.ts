import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsCreatePanel } from './alerts-create-panel.component';

const meta: Meta<AlertsCreatePanel> = {
  title: 'Components/AlertsCreatePanel',
  component: AlertsCreatePanel,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsCreatePanel>;

export const Primary: Story = {
  args: {},
};
