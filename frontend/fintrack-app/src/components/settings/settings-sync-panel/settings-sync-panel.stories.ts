import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsSyncPanel } from './settings-sync-panel.component';

const meta: Meta<SettingsSyncPanel> = {
  title: 'Components/SettingsSyncPanel',
  component: SettingsSyncPanel,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsSyncPanel>;

export const Primary: Story = {
  args: {},
};
